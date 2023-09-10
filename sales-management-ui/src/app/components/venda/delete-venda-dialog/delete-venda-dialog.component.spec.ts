import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeleteVendaDialogComponent } from './delete-venda-dialog.component';

describe('DeleteVendaDialogComponent', () => {
  let component: DeleteVendaDialogComponent;
  let fixture: ComponentFixture<DeleteVendaDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeleteVendaDialogComponent]
    });
    fixture = TestBed.createComponent(DeleteVendaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
