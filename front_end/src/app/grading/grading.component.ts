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
  correct_answer: string;
  expanded?: boolean;
}

interface Answer {
  id: number;
  providedAnswer: string;
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
  answers: Answer[] = [];
  currentAnswer?: Answer = undefined;
  currentAnswerIndex: number = 0;
  nrOfAnswers: number = 0;

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
    private readonly requirementService: RequirementService) {
    
  }

  hasChild = (_: number, node: FlatNode) => node.expandable;

  openProblem(id: number, nodeName: string) {
    this.problemOpen = true;
    this.problemData = nodeName;
    this.requirementService.getRequirementsByProblemId(id).subscribe( (data: any) => this.requirements = data);
  }

  toggleRequirement(selectedRequirement: Requirement){
    this.answerService.getAnswersByRequirementId(selectedRequirement.id).subscribe( (answers: any) => {
      this.answers = answers;
      this.currentAnswer =answers[0]; 
      this.currentAnswerIndex = 0;
      this.nrOfAnswers = answers.length;
    });
    this.requirements.forEach( requirement => {
      if( requirement === selectedRequirement ) {
        selectedRequirement.expanded = !selectedRequirement.expanded;
      } else {
        requirement.expanded = false;
      }
    })

  }

  loadNextAnswer(){
    this.currentAnswerIndex = (this.currentAnswerIndex + 1) %  this.nrOfAnswers;
    this.currentAnswer = this.answers[this.currentAnswerIndex];
  }

  loadPreviousAnswer() {
    if(this.currentAnswerIndex == 0){
      this.currentAnswerIndex = this.nrOfAnswers-1;
    } else {
      this.currentAnswerIndex--;
    }
    this.currentAnswer = this.answers[this.currentAnswerIndex];
  }
  
}
