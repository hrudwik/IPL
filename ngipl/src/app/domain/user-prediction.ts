export class UserPrediction {
    emailId:string;
    matchId:number;
    bestBatsmen:string;
    bestBowler:string;
    manOfTheMatch:string;
    winner:string;
    points:number;
    bet:number;
    winnings:number;
    constructor(emailId: string, matchId: number, bestBatsmen: string, bestBowler: string,
        manOfTheMatch: string, winner: string, points:number, bet:number, winnings:number){
            this.emailId = emailId;
            this.matchId = matchId;
            this.bestBatsmen = bestBatsmen;
            this.bestBowler = bestBowler;
            this.manOfTheMatch = manOfTheMatch;
            this.winner = winner;
            this.points = points;
            this.bet = bet;
            this.winnings = winnings;
        }
}

export class UserScorecard {
    userId:number;
    emailId:string;
    userName:string;
    points:number;
    money:number;
}