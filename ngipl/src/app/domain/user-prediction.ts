export class UserPrediction {
    emailId:string;
    matchId:number;
    bestBatsmen:string;
    bestBowler:string;
    manOfTheMatch:string;
    winner:string;
    constructor(emailId: string, matchId: number, bestBatsmen: string, bestBowler: string,
        manOfTheMatch: string, winner: string){
            this.emailId = emailId;
            this.matchId = matchId;
            this.bestBatsmen = bestBatsmen;
            this.bestBowler = bestBowler;
            this.manOfTheMatch = manOfTheMatch;
            this.winner = winner;
        }
}