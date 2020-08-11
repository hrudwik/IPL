import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from './user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private _http: HttpClient) { }

  public loginUserFromRemote(user: User):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/login", user);
  }

  public registerUserFromRemote(user: User):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/registeruser", user);
  }

  public matchDetailsFromRemote(matchId: String):Observable<any> {
    return this._http.post<any>("http://192.168.0.109:8080/matchDetails", matchId);
  }
}
