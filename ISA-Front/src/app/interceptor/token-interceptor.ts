import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { UserService } from "../service/user.service";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {
  constructor(public userService: UserService) { }
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (this.userService.tokenIsPresent()) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.userService.getToken()}` 
        }
      });
    }
    return next.handle(request);
  }
}