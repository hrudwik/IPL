import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from '../user';
import { RegistrationService } from '../registration.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user = new User();
  msg = '';
  sessionEmailId: string = ""

  constructor(private _service: RegistrationService, private _router: Router) { }

  ngOnInit(): void {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    if(this.sessionEmailId != null){
      this._router.navigate(['/home'])
    }
  }

  loginUser() {
    this._service.loginUserFromRemote(this.user).subscribe(
      data => {
        console.log("response received");
        sessionStorage.setItem("emailId", data.emailId)
        sessionStorage.setItem("userName", data.userName)
        sessionStorage.setItem("phoneNumber", data.phoneNumber)
        this._router.navigate(['/home'])
      },
      error => {
        console.log("exception occured");
        this.msg = "Bad credentials, please enter valid emailId and password";
      }
    )
  }

  gotoregistration() {
    this._router.navigate(['/registration'])
  }
}
