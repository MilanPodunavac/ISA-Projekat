import { TestBed } from '@angular/core/testing';

import { FishingInstructorService } from './fishing-instructor.service';

describe('FishingInstructorService', () => {
  let service: FishingInstructorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FishingInstructorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
