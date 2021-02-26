import { PartialScoreElementDTO } from './../../../build/openapi/model/partialScoreElementDTO';
import { PartialScoreElementsService } from './../../../build/openapi/api/partialScoreElements.service';
import { GroupedAnswerDTO } from './../../../build/openapi/model/groupedAnswerDTO';
import { RequirementService } from './../../../build/openapi/api/requirement.service';
import { AnswerService } from './../../../build/openapi/api/answer.service';
import { ProblemService } from './../../../build/openapi/api/problem.service';
import { Component, OnInit } from '@angular/core';
import { MatTreeFlatDataSource, MatTreeFlattener } from '@angular/material/tree';
import { FlatTreeControl } from '@angular/cdk/tree';
import { FormControl } from '@angular/forms';

interface ProblemContestNode {
  id?: number;
  name: string;
  children?: ProblemContestNode[];
}

interface Problem {
  id: number;
  name: string;
  contestName: string;
  sectionName: string;
}

interface Requirement {
  id: number;
  specification: string;
  correctAnswer: string;
  expanded?: boolean;
}

interface FlatNode {
  expandable: boolean;
  name: string;
  level: number;
  id?: number;
}


@Component({
  selector: 'app-grading',
  templateUrl: './grading.component.html',
  styleUrls: ['./grading.component.css']
})

export class GradingComponent implements OnInit {
  problemOpen = false;
  problemData = "";
  requirements: Requirement[] = [];
  answers: GroupedAnswerDTO[] = [];
  currentAnswer?: GroupedAnswerDTO = undefined;
  currentAnswerIndex: number = 0;
  totalNrOfAnswers: number = 0;
  partialScoreElements: PartialScoreElementDTO[] = [];
  scoreForCurrentAnswer: number = 0;

  ngOnInit(): void {
    this.problemService.listProblemsGroupedBySectionAndContest().subscribe( data =>
      this.dataSource.data = data)
  }

  private _transformer = (node: ProblemContestNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
      id:node.id
    };
  }

  treeControl = new FlatTreeControl<FlatNode, ProblemContestNode>(
      node => node.level, node => node.expandable);

  treeFlattener = new MatTreeFlattener<ProblemContestNode, FlatNode, ProblemContestNode>(
      this._transformer, node => node.level, node => node.expandable, node => node.children);

  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(
    private readonly problemService: ProblemService,
    private readonly answerService: AnswerService,
    private readonly requirementService: RequirementService,
    private readonly partialScoreElementsService: PartialScoreElementsService) {
    
  }

  hasChild = (_: number, node: FlatNode) => node.expandable;

  openProblem(id: number, nodeName: string) {
    this.problemOpen = true;
    this.problemData = nodeName;
    this.requirementService.getRequirementsByProblemId(id).subscribe( (data: any) => this.requirements = data);
  }

  toggleRequirement(selectedRequirement: Requirement){
    this.scoreForCurrentAnswer = 0;
    this.partialScoreElementsService.getPartialScoreElementsByRequirementId(selectedRequirement.id)
      .subscribe( (partialScoreElements: PartialScoreElementDTO[]) => {
        this.partialScoreElements = partialScoreElements;
      })


    this.answerService.getAnswersByRequirementIdGroupedByProvidedAnswer(selectedRequirement.id)
      .subscribe( (answers: GroupedAnswerDTO[]) => {
        this.answers = answers;
        this.currentAnswer = answers[0];
        this.currentAnswerIndex = 0;
        this.totalNrOfAnswers = 0;
        answers.forEach((element: GroupedAnswerDTO) => {
          this.totalNrOfAnswers += element.nrOfAnswers;
        });
    })
  }

  loadNextAnswer(){
    this.currentAnswerIndex = (this.currentAnswerIndex + 1) %  this.answers.length;
    this.currentAnswer = this.answers[this.currentAnswerIndex];
    this.scoreForCurrentAnswer = 0;
  }

  loadPreviousAnswer() {
    if(this.currentAnswerIndex == 0){
      this.currentAnswerIndex = this.answers.length-1;
    } else {
      this.currentAnswerIndex--;
    }
    this.currentAnswer = this.answers[this.currentAnswerIndex];
    this.scoreForCurrentAnswer = 0;
  }
  
togglePartialScoreElement(element: PartialScoreElementDTO) {
  this.scoreForCurrentAnswer += element.score;
  console.log(element.score);
}

}
