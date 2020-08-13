import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common'

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { PredictFormModalComponent } from '../predict-form-modal/predict-form-modal.component';
import { RegistrationService } from '../registration.service';
import { UserPrediction } from '../domain/user-prediction';

@Component({
  selector: 'app-predict',
  templateUrl: './predict.component.html',
  styleUrls: ['./predict.component.css']
})
export class PredictComponent implements OnInit {
  closeResult = '';
  sessionEmailId: string = ""
  matchDetails: MatchDetails[];
  match1: Match = new Match();
  match2: Match = new Match();
  match3: Match = new Match();
  imagePath1 = "";
  imagePath2 = "";
  imagePath3 = "";

  constructor(private _service: RegistrationService, private modalService: NgbModal, private _datePipe: DatePipe) { }

  ngOnInit(): void {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    this.nextThreeMatchDetails()
  }

  openFormModalFirst() {

    // let currentTimeStamp = Date.now();
    // let matchTimeStamp = new Date(this.matchDetails[0].scheduleDate).getTime();
    // console.log(currentTimeStamp);
    // console.log(matchTimeStamp);
    // if(currentTimeStamp>=matchTimeStamp) {
    //   console.log("Refreshing page");
    //   this.ngOnInit();
    //   return;
    // }
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.matchDetails = this.matchDetails[0]; // sending to modal form
    
    modalRef.result.then((result) => {
      console.log(result);
      let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[0].matchId,
        result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner);
      console.log(userPrediction);
      this.updateUserPrediction(userPrediction);
    }).catch((error) => {
      console.log(error);
    });
  }

  openFormModalSecond() {
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.matchDetails = this.matchDetails[1];; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
      let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[1].matchId,
        result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner);
      console.log(userPrediction);
      this.updateUserPrediction(userPrediction);
    }).catch((error) => {
      console.log(error);
    });
  }

  openFormModalThird() {
    const modalRef = this.modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.matchDetails = this.matchDetails[2];; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
      let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[2].matchId,
        result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner);
      console.log(userPrediction);
      this.updateUserPrediction(userPrediction);
    }).catch((error) => {
      console.log(error);
    });
  }

  updateUserPrediction(userPrediction: UserPrediction) {
    console.log("about to call updateUserPredictionFromRemote");
    this._service.updateUserPredictionFromRemote(userPrediction).subscribe(
      data => {
        console.log("updateUserPredictionFromRemote successful");
        console.log(data)
      },
      error => {
        console.log("exception occured while posting updateUserPredictionFromRemote");
      }
    )
  }



  nextThreeMatchDetails() {
    this._service.nextThreeMatchDetailsFromRemote().subscribe(
      data => {
        console.log("nextThreeMatchDetailsFromRemote response received");
        this.matchDetails = data as MatchDetails[]
        this.match1.imagePath = "../assets/img/Matches/" +this.matchDetails[0].matchId+ ".jpg";
        this.match2.imagePath = "../assets/img/Matches/" +this.matchDetails[1].matchId+ ".jpg";
        this.match3.imagePath = "../assets/img/Matches/" +this.matchDetails[2].matchId+ ".jpg";

        this.match1.matchTitle = ""+ this.matchDetails[0].teamName1 + " Vs " +this.matchDetails[0].teamName2;
        this.match2.matchTitle = ""+ this.matchDetails[1].teamName1 + " Vs " +this.matchDetails[1].teamName2;
        this.match3.matchTitle = ""+ this.matchDetails[2].teamName1 + " Vs " +this.matchDetails[2].teamName2;

        this.match1.time =this._datePipe.transform(this.matchDetails[0].scheduleDate, 'dd-MMMM (hh:mm a)');
        this.match2.time =this._datePipe.transform(this.matchDetails[1].scheduleDate, 'dd-MMMM (hh:mm a)');
        this.match3.time =this._datePipe.transform(this.matchDetails[2].scheduleDate, 'dd-MMMM (hh:mm a)');
        console.log(this.matchDetails)
      },
      error => {
        console.log("exception occured while fetching nextThreeMatchDetailsFromRemote");
      }
    )
  }

}


export class MatchDetails{
  matchId: number;
  teamName1: string;
  teamName2: string;
  scheduleDate: Date;
  players: string[];
}

export class Match{
  imagePath: string;
  matchTitle: string;
  time: string;
}
