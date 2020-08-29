import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common'
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { UserScorecard, UserPrediction } from '../domain/user-prediction';
import { MatchDetails, Match } from '../predict/predict.component';

@Component({
  selector: 'app-private-league',
  templateUrl: './private-league.component.html',
  styleUrls: ['./private-league.component.css']
})
export class PrivateLeagueComponent implements OnInit {
  
  sessionEmailId: string = ""
  matchDetails: MatchDetails[];
  matches: Match[] = [];
  userScorecard: UserScorecard[];
  userPrediction: UserPrediction[];
  myForm: FormGroup;

  constructor(private _service: RegistrationService, private formBuilder: FormBuilder, private _datePipe: DatePipe) {
    this.createForm();
   }

  private createForm() {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    this.myForm = this.formBuilder.group({
      match:  [null, [ Validators.required ] ]
    });
  }

  ngOnInit(): void {
    this.getAllMatchDetails();
    this.getOverallScorecard();
  }

  onChange(event) {

    let matchDetails: MatchDetails = new MatchDetails();
    matchDetails.matchId = parseInt(event.target.value, 10);

    this._service.getPaidLeaderboardForMatchFromRemote(matchDetails).subscribe(
      data => {
        console.log("getPaidLeaderboardForMatchFromRemote successful");
        this.userPrediction = data as UserPrediction[]
      },
      error => {
        console.log("exception occured while posting getLeaderboardForMatchFromRemote");
      }
    )
  }

  getOverallScorecard() {
    this._service.getOverallLeaderboardFromRemote().subscribe(
      data => {
        console.log("updateUserPredictionFromRemote successful");
        // console.log(data)
        this.userScorecard = data as UserScorecard[]
        //console.log("convert sucess")
        //console.log(this.userScorecard[0]);
      },
      error => {
        console.log("exception occured while posting updateUserPredictionFromRemote");
      }
    )
  }

  getAllMatchDetails() {
    this._service.getAllMatchDetailsFromRemote().subscribe(
      data => {
        console.log("getAllMatchDetailsFromRemote response received");
        this.matchDetails = data as MatchDetails[]
        console.log(this.matchDetails)

        for (var index in this.matchDetails) {
          let match: Match = new Match();
          match.matchId = this.matchDetails[index].matchId;
          match.matchTitle = ""+ this.matchDetails[index].teamName1 + " Vs " +this.matchDetails[index].teamName2
          + " {"+ this._datePipe.transform(this.matchDetails[index].scheduleDate, 'dd-MMM (hh:mm a)') +"}";
          console.log("pushing match:")
          console.log(match);
          this.matches.push(match);
        }
      },
      error => {
        console.log("exception occured while fetching nextThreeMatchDetailsFromRemote");
      }
    )
  }

}