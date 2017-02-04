import {Component} from '@angular/core';
import {LoginService} from '../services/login.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent{

  myLocalStorage;

  constructor (private loginService:LoginService) {
    this.myLocalStorage=localStorage;
  }


  onClick() {
    if (this.loginService.checkLogin()) {
      this.loginService.logout();
    }
  }

}
