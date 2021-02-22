import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProblemService {

  private PROBLEMS_REST_API_SERVER = "http://localhost:8080/problem/";

  constructor(private httpClient: HttpClient) { }

  public getProblems() {
    return this.httpClient.get(this.PROBLEMS_REST_API_SERVER);
  }

  public getRequirements(id: number) {
    return this.httpClient.get(this.PROBLEMS_REST_API_SERVER + id + "/requirements");
  }

  public getAnswers(id: number){
    return this.httpClient.get(this.PROBLEMS_REST_API_SERVER + "requirement/" + id + "/answers");
  }
}
