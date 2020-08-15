import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../registration.service';
import { UserScorecard } from '../domain/user-prediction';

@Component({
  selector: 'app-leaderboard',
  templateUrl: './leaderboard.component.html',
  styleUrls: ['./leaderboard.component.css']
})
export class LeaderboardComponent implements OnInit {

  userScorecard: UserScorecard[];

  constructor(private _service: RegistrationService) { }

  ngOnInit(): void {
    this.getOverallScorecard();
  }

  getOverallScorecard() {
    this._service.getOverallLeaderboardFromRemote().subscribe(
      data => {
        console.log("updateUserPredictionFromRemote successful");
        console.log(data)
        this.userScorecard = data as UserScorecard[]
        console.log("convert sucess")
        console.log(this.userScorecard[0]);
      },
      error => {
        console.log("exception occured while posting updateUserPredictionFromRemote");
      }
    )
  }

}
