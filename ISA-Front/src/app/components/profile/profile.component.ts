import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CalendarOptions, DateSelectArg } from '@fullcalendar/angular';
import { AdminGet } from 'src/app/model/admin-get.model';
import { CalendarEvent } from 'src/app/model/calendar-event.model';
import { ClientGet } from 'src/app/model/client-get';
import { FishingInstructorAvailablePeriodGet } from 'src/app/model/fishing-instructor-available-period-get.model';
import { FishingInstructorGet } from 'src/app/model/fishing-instructor-get';
import { FishingTripGet } from 'src/app/model/fishing-trip-get';
import { LoyaltyProgramProvider } from 'src/app/model/loyalty-program-provider.model';
import { AdminService } from 'src/app/service/admin.service';
import { ClientService } from 'src/app/service/client.service';
import { FishingInstructorService } from 'src/app/service/fishing-instructor.service';
import { FishingTripService } from 'src/app/service/fishing-trip.service';
import { LoyaltyProgramService } from 'src/app/service/loyalty-program.service';
import { UserService } from 'src/app/service/user.service';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    role: string;
    fishingInstructorData: FishingInstructorGet;
    adminData: AdminGet;
    clientData: ClientGet;
    loyaltyProgramCategoryAboveInstructor: LoyaltyProgramProvider;
    displayedColumnsFishingInstructorAvailablePeriods: string[] = ['available_from', 'available_to'];
    dataSourceFishingInstructorAvailablePeriods: FishingInstructorAvailablePeriodGet[];
    user: any;
    calendarOptions: CalendarOptions = {
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek'
        },
        initialView: 'dayGridMonth',
        selectable: true,
        select: this.handleDateSelect.bind(this)
    };

    handleDateSelect(selectInfo: DateSelectArg) {
        const calendarApi = selectInfo.view.calendar;
    
        calendarApi.unselect(); 
    
        let availabilityPeriod = new FishingInstructorAvailablePeriodGet();
        availabilityPeriod.availableFrom = new Date(selectInfo.startStr);
        let end = new Date();
        end.setDate(new Date(new Date(selectInfo.endStr)).getDate());
        end.setMonth(new Date(new Date(selectInfo.endStr)).getMonth());
        end.setFullYear(new Date(new Date(selectInfo.endStr)).getFullYear());
        end.setDate(end.getDate() - 1);
        availabilityPeriod.availableTo = end;

        this.fishingInstructorService.addAvailabilityPeriod(availabilityPeriod).subscribe({
            next: data => {
                this.router.navigate(['profile']).then(() => {
                    window.location.reload();
                });
                alert(data);
            },
            error: error => {
                alert(error.error);
            }
        });
    }

    constructor(private fishingInstructorService: FishingInstructorService, private clientService: ClientService, private loyaltyProgramService: LoyaltyProgramService, private _usersService: UserService, private router: Router, private adminService: AdminService, private fishingTripService: FishingTripService, private datePipe: DatePipe) {
        
        this.role = localStorage.getItem('role');        
        
        if(this.role === 'ROLE_FISHING_INSTRUCTOR'){
            this.fishingInstructorService.getLoggedInInstructor().subscribe(data => {
                this.fishingInstructorData = data;

                this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.fishingInstructorData.category.id).subscribe(data => {
                    this.loyaltyProgramCategoryAboveInstructor = data;
                });
            });

            this.fishingInstructorService.getFishingInstructorAvailablePeriods().subscribe(data => {
                this.dataSourceFishingInstructorAvailablePeriods = data;
                let availablePeriodsCalendar : CalendarEvent[] = []
                for (let i = 0; i < this.dataSourceFishingInstructorAvailablePeriods.length; i++) {
                    let availablePeriodCalendar = new CalendarEvent();
                    let end = new Date();
                    end.setDate(new Date(this.dataSourceFishingInstructorAvailablePeriods[i].availableTo).getDate());
                    end.setMonth(new Date(this.dataSourceFishingInstructorAvailablePeriods[i].availableTo).getMonth());
                    end.setFullYear(new Date(this.dataSourceFishingInstructorAvailablePeriods[i].availableTo).getFullYear());
                    end.setDate(end.getDate() + 1);
                    availablePeriodCalendar.start = this.datePipe.transform(this.dataSourceFishingInstructorAvailablePeriods[i].availableFrom, "yyyy-MM-dd");
                    availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
                    availablePeriodCalendar.display = 'background';
                    availablePeriodCalendar.overlap = false;
                    availablePeriodsCalendar.push(availablePeriodCalendar);
                }

                this.fishingTripService.getFishingInstructorReservations().subscribe(data => {
                    for (let i = 0; i < data.length; i++) {
                        let availablePeriodCalendar = new CalendarEvent();
                        let end = new Date();
                        end.setDate(new Date(data[i].start).getDate());
                        end.setMonth(new Date(data[i].start).getMonth());
                        end.setFullYear(new Date(data[i].start).getFullYear());
                        end.setDate(end.getDate() + data[i].durationInDays);
                        availablePeriodCalendar.title = data[i].client.firstName + " " + data[i].client.lastName;
                        availablePeriodCalendar.start = this.datePipe.transform(data[i].start, "yyyy-MM-dd");
                        availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
                        availablePeriodCalendar.color = 'blue';
                        availablePeriodCalendar.overlap = false;
                        availablePeriodsCalendar.push(availablePeriodCalendar);
                    }

                    this.fishingTripService.getFishingInstructorActions().subscribe(data => {
                        for (let i = 0; i < data.length; i++) {
                            let availablePeriodCalendar = new CalendarEvent();
                            let end = new Date();
                            end.setDate(new Date(data[i].start).getDate());
                            end.setMonth(new Date(data[i].start).getMonth());
                            end.setFullYear(new Date(data[i].start).getFullYear());
                            end.setDate(end.getDate() + data[i].durationInDays);
                            if (data[i].client) {
                                availablePeriodCalendar.title = data[i].client.firstName + " " + data[i].client.lastName;
                            }
                            availablePeriodCalendar.start = this.datePipe.transform(data[i].start, "yyyy-MM-dd");
                            availablePeriodCalendar.end = this.datePipe.transform(end, "yyyy-MM-dd");
                            availablePeriodCalendar.color = 'red';
                            availablePeriodCalendar.overlap = false;
                            availablePeriodsCalendar.push(availablePeriodCalendar);
                        }

                        this.calendarOptions.events = availablePeriodsCalendar;
                    });
                });
            });
        }
        if(this.role === 'ROLE_ADMIN'){
            this.adminService.getLoggedInAdmin().subscribe(data => {
                this.adminData = data;
            });
        }
        if(this.role === 'ROLE_CLIENT'){
            this.clientService.getLoggedInClient().subscribe(data => {
                console.log(data)
                this.clientData = data;
            });
        }
        if(this.role === "ROLE_COTTAGE_OWNER" || this.role === "ROLE_BOAT_OWNER"){
            this._usersService.getLoggedInUser().subscribe(
                {next: data => {
                  this.user = data;
                  this.loyaltyProgramService.getOneHigherLoyaltyProviderCategory(this.user.category.id).subscribe(data => {
                    this.loyaltyProgramCategoryAboveInstructor = data;
                    });
                },
                error: data => {
                  console.log(data)
                  alert(data.error)
                }
            });
        }
    }

    ngOnInit(): void {
    }

    public goToChangePersonalData(): void {
        this.router.navigate(['change-personal-data']).then(() => {
            window.location.reload();
        });
    }

    public goToChangePassword(): void {
        this.router.navigate(['change-password']).then(() => {
            window.location.reload();
        });
    }

    public goToAccountDeletion(): void {
        this.router.navigate(['account-deletion-request']).then(() => {
            window.location.reload();
        });
    }

    public goToAddAvailabilityPeriod(): void {
        this.router.navigate(['add-availability-period']).then(() => {
            window.location.reload();
        });
    }

    public goToBusinessReport(): void {
        if (this.role === 'ROLE_ADMIN') {
            this.router.navigate(['business-report-admin']).then(() => {
                window.location.reload();
            });
        } else if (this.role === 'ROLE_FISHING_INSTRUCTOR') {
            this.router.navigate(['business-report']).then(() => {
                window.location.reload();
            });
        }
    }
}
