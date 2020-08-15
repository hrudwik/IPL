export class UserPrediction {
    emailId:string;
    matchId:number;
    bestBatsmen:string;
    bestBowler:string;
    manOfTheMatch:string;
    winner:string;
    points:number;
    constructor(emailId: string, matchId: number, bestBatsmen: string, bestBowler: string,
        manOfTheMatch: string, winner: string, points:number){
            this.emailId = emailId;
            this.matchId = matchId;
            this.bestBatsmen = bestBatsmen;
            this.bestBowler = bestBowler;
            this.manOfTheMatch = manOfTheMatch;
            this.winner = winner;
            this.points = points;
        }
}

export class UserScorecard {
    userId:number;
    emailId:string;
    userName:string;
    points:number;
    // constructor(userId: number, emailId: string, userName: string, points: number){
    //         this.userId = userId;
    //         this.emailId = emailId;
    //         this.userName = userName;
    //         this.points = points;
    //     }
}