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

  @Input() public matchDetails: MatchDetails;
  myForm: FormGroup;

  constructor(private _service: RegistrationService, public activeModal: NgbActiveModal,
    private formBuilder: FormBuilder) {
      this.createForm();
  }

  private createForm() {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    this.myForm = this.formBuilder.group({
      winner:  [null, [ Validators.required ] ],
      manOfTheMatch:  [null, [ Validators.required ] ],
      bestBatsmen:  [null, [ Validators.required ] ],
      bestBowler:  [null, [ Validators.required ] ]
    });
  }

  public submitForm() {
    this.activeModal.close(this.myForm.value);
  }

  getUserPredictionFromServer() {

    let userPrediction: UserPrediction = new UserPrediction(this.sessionEmailId, this.matchDetails.matchId,
      null, null, null, null, null);

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

        this.myForm.setValue({
          winner: userPrediction.winner, 
          manOfTheMatch: userPrediction.manOfTheMatch,
          bestBatsmen: userPrediction.bestBatsmen,
          bestBowler: userPrediction.bestBowler,
        });
      },
      error => {
        console.log("exception occured while fetching getUserPredictionFromRemote");
      }
    )
  }

  matchMatchDetails() {

    this.players = this.matchDetails.players;
    this.teams.push(this.matchDetails.teamName1);
    this.teams.push(this.matchDetails.teamName2);
  }


  ngOnInit(): void {
    this.matchMatchDetails();
    this.getUserPredictionFromServer();
  }

  closeModal() {
    this.activeModal.close('Modal Closed');
  }

}