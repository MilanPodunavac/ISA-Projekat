import { Component, OnInit } from '@angular/core';
import { ConfigService } from 'src/app/service/config.service';
import { FooService } from 'src/app/service/foo.service';
import { UserService } from 'src/app/service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  fooResponse = {};
  jooResponse = {};
  booResponse = {};
  nooResponse = {};
  whoamIResponse = {};
  allUserResponse = {};

  constructor(
    private config: ConfigService,
    private fooService: FooService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
  }

  makeRequest(path) {
    if (this.config.foo_url.endsWith(path)) {
      this.fooService.getFoo()
        .subscribe(res => {
          this.forgeResonseObj(this.fooResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.fooResponse, err, path);
        });
    } else if (this.config.joo_url.endsWith(path)) {
      this.fooService.getJoo()
        .subscribe(res => {
          this.forgeResonseObj(this.jooResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.jooResponse, err, path);
        });
    } else if (this.config.boo_url.endsWith(path)) {
      this.fooService.getBoo()
        .subscribe(res => {
          this.forgeResonseObj(this.booResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.booResponse, err, path);
        });
    } else if (this.config.noo_url.endsWith(path)) {
      this.fooService.getNoo()
        .subscribe(res => {
          this.forgeResonseObj(this.nooResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.nooResponse, err, path);
        });
    } else if (this.config.whoami_url.endsWith(path)) {
      this.userService.getMyInfo()
        .subscribe(res => {
          this.forgeResonseObj(this.whoamIResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.whoamIResponse, err, path);
        });
    } else {
      this.userService.getAll()
        .subscribe(res => {
          this.forgeResonseObj(this.allUserResponse, res, path);
        }, err => {
          this.forgeResonseObj(this.allUserResponse, err, path);
        });
    }
  }

  forgeResonseObj(obj, res, path) {
    obj['path'] = path;
    obj['method'] = 'GET';
    if (res.ok === false) {
      // err
      obj['status'] = res.status;
      try {
        obj['body'] = JSON.stringify(JSON.parse(res._body), null, 2);
      } catch (err) {
        console.log(res);
        obj['body'] = res.error.message;
      }
    } else {
      // 200
      obj['status'] = 200;
      obj['body'] = JSON.stringify(res, null, 2);
    }
  }
}
