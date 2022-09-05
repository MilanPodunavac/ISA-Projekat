import { TestBed } from '@angular/core/testing';

import { BoatOwnerService } from './boat-owner.service';

describe('BoatOwnerService', () => {
  let service: BoatOwnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BoatOwnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
