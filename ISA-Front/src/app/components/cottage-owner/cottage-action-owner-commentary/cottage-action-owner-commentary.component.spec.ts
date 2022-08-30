import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageActionOwnerCommentaryComponent } from './cottage-action-owner-commentary.component';

describe('CottageActionOwnerCommentaryComponent', () => {
  let component: CottageActionOwnerCommentaryComponent;
  let fixture: ComponentFixture<CottageActionOwnerCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageActionOwnerCommentaryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageActionOwnerCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
