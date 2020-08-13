import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  sessionEmailId: string = ""

  constructor(private _router: Router) { }

  ngOnInit(): void {
    this.sessionEmailId = sessionStorage.getItem("emailId");
    if(this.sessionEmailId == null){
      this._router.navigate(['/login'])
    }
  }

}
