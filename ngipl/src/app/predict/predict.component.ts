import { Component, OnInit, Type } from '@angular/core';
import { DatePipe } from '@angular/common'

import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { PredictFormModalComponent } from '../predict-form-modal/predict-form-modal.component';
import { RegistrationService } from '../registration.service';
import { UserPrediction } from '../domain/user-prediction';
import {Subject} from 'rxjs';
import {debounceTime} from 'rxjs/operators';

@Component({
  selector: 'app-predict',
  templateUrl: './predict.component.html',
  styleUrls: ['./predict.component.css']
})
export class PredictComponent implements OnInit {
  private _success = new Subject<string>();
  staticAlertClosed = false;
  successMessage = '';
  closeResult = '';
  sessionEmailId: string = ""
  matchDetails: MatchDetails[];
  match1: Match = new Match();
  match2: Match = new Match();
  match3: Match = new Match();
  imagePath1 = "";
  imagePath2 = "";
  imagePath3 = "";
  withAutofocus = `<button type="button" ngbAutofocus class="btn btn-danger"
      (click)="modal.close('Ok click')">Ok</button>`;

  constructor(private _service: RegistrationService, private _modalService: NgbModal, private _datePipe: DatePipe) { }

  ngOnInit(): void {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    setTimeout(() => this.staticAlertClosed = true, 20000);

    this._success.subscribe(message => this.successMessage = message);
    this._success.pipe(
      debounceTime(5000)
    ).subscribe(() => this.successMessage = '');

    this.nextThreeMatchDetails()
  }

  updatePredictedButtonColor() {
    let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.match1.matchId,
      null, null, null, null, null, null, null);
      console.log("yo, bud");
      console.log(userPrediction);
    this._service.getUserPredictionFromRemote(userPrediction).subscribe(
      data => {
        console.log("getUserPredictionFromRemote successful");
        console.log(data)
        if(data != null && data.winner != null && data.winner != "") {
          this.match1.freeButton = "btn btn-outline-success mr-3";
        } else {
          this.match1.freeButton = "btn btn-outline-primary mr-3";
        }
        if(data != null && data.bet>0){
          this.match1.paidButton = "btn btn-outline-success";
        } else {
          this.match1.paidButton = "btn btn-outline-primary";
        }
      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
      }
    )

    userPrediction.matchId = this.match2.matchId;
    this._service.getUserPredictionFromRemote(userPrediction).subscribe(
      data => {
        console.log("getUserPredictionFromRemote successful");
        console.log(data)
        if(data != null && data.winner != null && data.winner != "") {
          this.match2.freeButton = "btn btn-outline-success mr-3";
        } else {
          this.match2.freeButton = "btn btn-outline-primary mr-3";
        }
        if(data != null && data.bet>0){
          this.match2.paidButton = "btn btn-outline-success";
        } else {
          this.match2.paidButton = "btn btn-outline-primary";
        }
      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
      }
    )

    userPrediction.matchId = this.match3.matchId;
    this._service.getUserPredictionFromRemote(userPrediction).subscribe(
      data => {
        console.log("getUserPredictionFromRemote successful");
        console.log(data)
        if(data != null && data.winner != null && data.winner != "") {
          this.match3.freeButton = "btn btn-outline-success mr-3";
        } else {
          this.match3.freeButton = "btn btn-outline-primary mr-3";
        }
        if(data != null && data.bet>0){
          this.match3.paidButton = "btn btn-outline-success";
        } else {
          this.match3.paidButton = "btn btn-outline-primary";
        }
      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
      }
    )
  }

  open(name: string, matchNo: string, amount: number) {

    let currentTimeStamp = Date.now();
    let matchTimeStamp = new Date(this.matchDetails[Number(matchNo)].scheduleDate).getTime();
    console.log(currentTimeStamp);
    console.log(matchTimeStamp);
    if(currentTimeStamp>=matchTimeStamp) {
      console.log("Refreshing page");
      this._success.next(`Match prediction timed out. Please refresh your page.`);
      return;
    }

    this._service.getOverallLeaderboardFromRemote().subscribe(
      data => {
        console.log("money check call");
        console.log(data)

        let modalRef = null;
        if(data != null) {
          for (var userScorecard of data) {
            if(userScorecard.emailId == this.sessionEmailId){
              if(userScorecard.money<amount) {
                console.log("no money");
                modalRef = this._modalService.open(MODALS["insufficientFunds"]);
              } else {
                modalRef = this._modalService.open(MODALS[name]);
      
                modalRef.result.then((result) => {
                  console.log(result);
                  if(result == 'Ok') {
                    if(matchNo == '0') {
                      let isSucceeded = this.openFormModalFirst(amount);
                      console.log(isSucceeded);
                      if(isSucceeded == false){
                        this._success.next(`Match prediction timed out. Please refresh your page.`);
                      }
                    } else if(matchNo == '1'){
                      let isSucceeded = this.openFormModalSecond(amount);
                      if(isSucceeded == false){
                        this._success.next(`Match prediction timed out. Please refresh your page.`);
                      }
                    } else if(matchNo == '2') {
                      let isSucceeded = this.openFormModalThird(amount);
                      if(isSucceeded == false){
                        this._success.next(`Match prediction timed out. Please refresh your page.`);
                      }
                    }
                  }
                }).catch((error) => {
                  console.log(error)
                });
              }
            }
          } 
        } 

      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
      }
    )
  }

  openFormModalFirst(amount: number): boolean {

    const modalRef = this._modalService.open(PredictFormModalComponent);
      modalRef.componentInstance.matchDetails = this.matchDetails[0]; // sending to modal form
      
      modalRef.result.then((result) => {
        console.log(result);
        let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[0].matchId,
          result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner, result.points, amount, result.winnings);
        console.log(userPrediction);

        let currentTimeStamp = Date.now();
        let matchTimeStamp = new Date(this.matchDetails[0].scheduleDate).getTime();
        console.log(currentTimeStamp);
        console.log(matchTimeStamp);
        if(currentTimeStamp>=matchTimeStamp) {
          console.log(`Match prediction timed out. Please refresh your page.`);
          this._success.next(`Match prediction timed out. Please refresh your page.`);
          return false;
        } else {
          this.updateUserPrediction(userPrediction);
        }

      }).catch((error) => {
        console.log(error);
      });
      return true;
  }

  openFormModalSecond(amount: number) {
    const modalRef = this._modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.matchDetails = this.matchDetails[1];; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
      let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[1].matchId,
        result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner, result.points, amount, result.winnings);
      console.log(userPrediction);

      let currentTimeStamp = Date.now();
      let matchTimeStamp = new Date(this.matchDetails[1].scheduleDate).getTime();
      console.log(currentTimeStamp);
      console.log(matchTimeStamp);
      if(currentTimeStamp>=matchTimeStamp) {
        console.log(`Match prediction timed out. Please refresh your page.`);
        this._success.next(`Match prediction timed out. Please refresh your page.`);
        return false;
      } else {
        this.updateUserPrediction(userPrediction);
      }
    }).catch((error) => {
      console.log(error);
    });
    return true;
  }

  openFormModalThird(amount: number) {
    const modalRef = this._modalService.open(PredictFormModalComponent);
    modalRef.componentInstance.matchDetails = this.matchDetails[2];; // should be the id
    
    modalRef.result.then((result) => {
      console.log(result);
      let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails[2].matchId,
        result.bestBatsmen, result.bestBowler, result.manOfTheMatch, result.winner, result.points, amount, result.winnings);
      console.log(userPrediction);

      let currentTimeStamp = Date.now();
      let matchTimeStamp = new Date(this.matchDetails[2].scheduleDate).getTime();
      console.log(currentTimeStamp);
      console.log(matchTimeStamp);
      if(currentTimeStamp>=matchTimeStamp) {
        console.log(`Match prediction timed out. Please refresh your page.`);
        this._success.next(`Match prediction timed out. Please refresh your page.`);
        return false;
      } else {
        this.updateUserPrediction(userPrediction);
      }
    }).catch((error) => {
      console.log(error);
    });
    return true;
  }

  updateUserPrediction(userPrediction: UserPrediction) {
    console.log("about to call updateUserPredictionFromRemote");
    console.log(userPrediction);
    this._service.updateUserPredictionFromRemote(userPrediction).subscribe(
      data => {
        console.log("updateUserPredictionFromRemote successful");
        console.log(data)

        if(userPrediction.matchId == this.match1.matchId) {
            if(userPrediction.bet>0){
              this.match1.paidButton = "btn btn-outline-success";
            } else {
              this.match1.freeButton = "btn btn-outline-success mr-3";
            }
        } else if(userPrediction.matchId == this.match2.matchId) {
          if(userPrediction.bet>0){
            this.match2.paidButton = "btn btn-outline-success";
          } else {
            this.match2.freeButton = "btn btn-outline-success mr-3";
          }
        } else if(userPrediction.matchId == this.match3.matchId) {
          if(userPrediction.bet>0){
            this.match3.paidButton = "btn btn-outline-success";
          } else {
            this.match3.freeButton = "btn btn-outline-success mr-3";
          }
        }
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
        this.match1.matchId = this.matchDetails[0].matchId;
        this.match2.matchId = this.matchDetails[1].matchId;
        this.match3.matchId = this.matchDetails[2].matchId;

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
        this.updatePredictedButtonColor()
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
  matchId: number;
  imagePath: string;
  matchTitle: string;
  freeButton: string;
  paidButton: string;
  time: string;
}

@Component({
  selector: 'ngbd-modal-confirm-autofocus',
  template: `
  <div class="modal-header">
    <h4 class="modal-title" id="modal-title">Paid League</h4>
    <button type="button" class="close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p><strong>Are you sure you want to join <span class="text-primary">Paid League</span> ?</strong></p>
    <p>Great! Predict, sit back, relax n track your leaderboard post match.
    <span class="text-danger">You will be rewarded, if you win.</span>
    </p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('Cancel')">Cancel</button>
    <button type="button" ngbAutofocus class="btn btn-danger" (click)="modal.close('Ok')">Ok</button>
  </div>
  `
})
export class NgbdModalPaidLeague {
  constructor(public modal: NgbActiveModal) {}
}


@Component({
  selector: 'ngbd-modal-confirm-autofocus',
  template: `
  <div class="modal-header">
    <h4 class="modal-title" id="modal-title">Free League</h4>
    <button type="button" class="close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p><strong>Are you sure you want to join <span class="text-primary">Free League</span> ?</strong></p>
    <p>Free league is for fun play, you can track your rank in leaderboard.
    <span class="text-danger">However, you will not be rewarded in Free league.</span>
    </p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('Cancel')">Cancel</button>
    <button type="button" ngbAutofocus class="btn btn-danger" (click)="modal.close('Ok')">Ok</button>
  </div>
  `
})
export class NgbdModalFreeLeague {
  constructor(public modal: NgbActiveModal) {}
}

@Component({
  selector: 'ngbd-modal-confirm-autofocus',
  template: `
  <div class="modal-header">
    <h4 class="modal-title" id="modal-title">Timeout</h4>
    <button type="button" class="close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p><strong>Sorry, you cannot predict for past matches. <span class="text-primary">Free League</span> ?</strong></p>
    <p>Please refresh the page and try again.</p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('Cancel')">Cancel</button>
    <button type="button" ngbAutofocus class="btn btn-danger" (click)="modal.close('Ok')">Ok</button>
  </div>
  `
})
export class MatchExpired {
  constructor(public modal: NgbActiveModal) {}
}

@Component({
  selector: 'ngbd-modal-confirm-autofocus',
  template: `
  <div class="modal-header">
    <h4 class="modal-title" id="modal-title">Insufficient Funds</h4>
    <button type="button" class="close" aria-label="Close button" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <p><strong> <span class="text-primary">Sorry, you don't have enough balance</span> !</strong></p>
    <p>Please add amount to continue. If you have already joined paid league, then you can update your prediction using <strong>Join for Free</strong> option.</p>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-outline-secondary" (click)="modal.dismiss('Cancel')">Cancel</button>
    <button type="button" ngbAutofocus class="btn btn-danger" (click)="modal.close('Ok')">Ok</button>
  </div>
  `
})
export class InsufficientFunds {
  constructor(public modal: NgbActiveModal) {}
}

const MODALS: {[name: string]: Type<any>} = {
  freeLeague: NgbdModalFreeLeague,
  paidLeague: NgbdModalPaidLeague,
  matchExpired: MatchExpired,
  insufficientFunds: InsufficientFunds
};