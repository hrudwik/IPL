import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { MatchDetails } from '../predict/predict.component';
import { RegistrationService } from '../registration.service';
import { UserPrediction } from '../domain/user-prediction';

@Component({
  selector: 'app-predict-form-modal',
  templateUrl: './predict-form-modal.component.html',
  styleUrls: ['./predict-form-modal.component.css']
})
export class PredictFormModalComponent implements OnInit {

  sessionEmailId: string = ""
  teams: String[] = []
  players: String[] = []
  predictResult: PredictResult = new PredictResult();

  @Input() public matchDetails: MatchDetails;
  myForm: FormGroup;

  constructor(private _service: RegistrationService, public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder) {
      // this.createForm();
  }

  private createForm() {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    this.getUserPredictionFromServer();
  }

  public submitForm() {
    this.activeModal.close(this.myForm.value);
  }

  getUserPredictionFromServer() {

    let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails.matchId,
      null, null, null, null);

    this._service.getUserPredictionFromRemote(userPrediction).subscribe(
      data => {

        console.log("getUserPredictionFromRemote successful");
        console.log(data)
        if(data != null) {
          userPrediction.winner = data.winner;
          userPrediction.manOfTheMatch = data.manOfTheMatch;
          userPrediction.bestBatsmen = data.bestBatsmen;
          userPrediction.bestBowler = data.bestBowler;
        }

        this.myForm = this.formBuilder.group({
          winner:  [userPrediction.winner, [ Validators.required ] ],
          manOfTheMatch:  [userPrediction.manOfTheMatch, [ Validators.required ] ],
          bestBatsmen:  [userPrediction.bestBatsmen, [ Validators.required ] ],
          bestBowler:  [userPrediction.bestBowler, [ Validators.required ] ]
        });
      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
        this.myForm = this.formBuilder.group({
          winner:  [null, [ Validators.required ] ],
          manOfTheMatch:  [null, [ Validators.required ] ],
          bestBatsmen:  [null, [ Validators.required ] ],
          bestBowler:  [null, [ Validators.required ] ]
        });
      }
    )
  }

  matchMatchDetails() {

    this.players = this.matchDetails.players;
    this.teams.push(this.matchDetails.teamName1);
    this.teams.push(this.matchDetails.teamName2);
  }


  ngOnInit(): void {
    this.createForm();
    this.matchMatchDetails();
  }

  closeModal() {
    this.activeModal.close('Modal Closed');
  }

}

export class PredictResult{
  winner: string;
  manOfTheMatch: string;
  bestBatsmen: string;
  bestBowler: Date;
}
