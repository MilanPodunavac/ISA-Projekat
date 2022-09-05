import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SystemEntitiesComponent } from './system-entities.component';

describe('SystemEntitiesComponent', () => {
  let component: SystemEntitiesComponent;
  let fixture: ComponentFixture<SystemEntitiesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SystemEntitiesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SystemEntitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
