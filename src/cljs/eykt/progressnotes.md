## call-graphs

```
eykt.spa/front
  eykt.hoc/new-booking
    booking.views/booking-form -- fork/form
      booking.time-navigator/time-navigator
      >>match-state
        ::
        booking.views/confirmation
        ::
        booking.views.picker/boat-picker
          booking.views.picker/boat-list
            >>loop
              booking.view.pickers/list-line                
                booking.view.pickers/expanded-view
                  ..
                  booking.view.pickersdraw-graph 
```

```
schpaa.modal.readymade/are-you-sure
schpaa.modal.readymade/confirm-booking
    schpaa.modal.readymade/modal-booking-title2
    schpaa.modal.readymade/modal-form2
    ::
    schpaa.modal/form-action
    
```