import {Injectable} from '@angular/core';
import {ApiService} from './api.service';
import {ConfigService} from './config.service';

@Injectable()
export class FooService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) {
  }

  getFoo() {
    return this.apiService.get(this.config.foo_url);
  }

  getJoo() {
    return this.apiService.get(this.config.joo_url);
  }

  getBoo() {
    return this.apiService.get(this.config.boo_url);
  }

  getNoo() {
    return this.apiService.get(this.config.noo_url);
  }
}
