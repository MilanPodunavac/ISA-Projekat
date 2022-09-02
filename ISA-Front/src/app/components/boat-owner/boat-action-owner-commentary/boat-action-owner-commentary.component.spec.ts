import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatActionOwnerCommentaryComponent } from './boat-action-owner-commentary.component';

describe('BoatActionOwnerCommentaryComponent', () => {
  let component: BoatActionOwnerCommentaryComponent;
  let fixture: ComponentFixture<BoatActionOwnerCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatActionOwnerCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatActionOwnerCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
