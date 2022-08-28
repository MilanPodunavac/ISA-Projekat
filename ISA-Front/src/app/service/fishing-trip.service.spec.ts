import { TestBed } from '@angular/core/testing';

import { FishingTripService } from './fishing-trip.service';

describe('FishingTripService', () => {
  let service: FishingTripService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FishingTripService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
