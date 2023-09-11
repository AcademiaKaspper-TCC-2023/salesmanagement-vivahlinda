import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewVendaDialogComponent } from './view-venda-dialog.component';

describe('ViewVendaDialogComponent', () => {
  let component: ViewVendaDialogComponent;
  let fixture: ComponentFixture<ViewVendaDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewVendaDialogComponent]
    });
    fixture = TestBed.createComponent(ViewVendaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
