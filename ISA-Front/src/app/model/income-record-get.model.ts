export class IncomeRecordGet {
    id: number;
    reserved: boolean;
    dateOfEntry: Date;
    reservationStart: Date;
    reservationEnd: Date;
    reservationPrice: number;
    systemTaxPercentage: number;
    percentageProviderKeepsIfReservationCancelled: number;
    systemIncome: number;
    providerIncome: number;
    reservationProvider: any;
}
