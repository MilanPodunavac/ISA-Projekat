import { TestBed } from '@angular/core/testing';

import { CottageOwnerService } from './cottage-owner.service';

describe('CottageOwnerService', () => {
  let service: CottageOwnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CottageOwnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
