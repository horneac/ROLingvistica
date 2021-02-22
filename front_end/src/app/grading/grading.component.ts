import { ProblemService } from './../problem.service';
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
    this.problemService.getProblems().subscribe((data: any) => {
      this.dataSource.data = this._createTree(data);
      
    })
  }

  private _createTree(data:Problem[]): ProblemContestNode[] {
    var contests: ProblemContestNode[] = [];
    data.forEach(function(item) {
      //debugger;
      var problemNode: ProblemContestNode = {name: item.name, id: item.id};
      var contestNode = contests.find(x => x.name == item.contestName);
      if(contestNode === undefined){
        contestNode = {name:item.contestName, children: []};
        contests.push(contestNode);
      }
      var sectionNode = contestNode.children?.find(x => x.name == item.sectionName);
      if(sectionNode === undefined) {
        sectionNode = {name: item.sectionName, children: []};
        contestNode.children?.push(sectionNode);
      }
      sectionNode.children?.push(problemNode);
});
    console.log(contests);
    return contests;
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

  constructor(private problemService:ProblemService) {
    //this.dataSource.data = this.treeData;
  }

  hasChild = (_: number, node: FlatNode) => node.expandable;

  openProblem(id: number, nodeName: string) {
    this.problemOpen = true;
    this.problemData = nodeName;
    this.problemService.getRequirements(id).subscribe( (data: any) => this.requirements = data);
  }

  toggleRequirement(selectedRequirement: Requirement){
    this.problemService.getAnswers(selectedRequirement.id).subscribe( (answers: any) => {
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
