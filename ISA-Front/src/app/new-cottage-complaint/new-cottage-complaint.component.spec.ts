import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCottageComplaintComponent } from './new-cottage-complaint.component';

describe('NewCottageComplaintComponent', () => {
  let component: NewCottageComplaintComponent;
  let fixture: ComponentFixture<NewCottageComplaintComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewCottageComplaintComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewCottageComplaintComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
