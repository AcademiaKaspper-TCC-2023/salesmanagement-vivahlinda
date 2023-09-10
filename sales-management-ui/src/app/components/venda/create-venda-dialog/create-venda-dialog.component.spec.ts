import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateVendaDialogComponent } from './create-venda-dialog.component';

describe('CreateVendaDialogComponent', () => {
  let component: CreateVendaDialogComponent;
  let fixture: ComponentFixture<CreateVendaDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateVendaDialogComponent]
    });
    fixture = TestBed.createComponent(CreateVendaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
