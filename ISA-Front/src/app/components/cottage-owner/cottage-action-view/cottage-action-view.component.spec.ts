import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageActionViewComponent } from './cottage-action-view.component';

describe('CottageActionViewComponent', () => {
  let component: CottageActionViewComponent;
  let fixture: ComponentFixture<CottageActionViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageActionViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageActionViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
