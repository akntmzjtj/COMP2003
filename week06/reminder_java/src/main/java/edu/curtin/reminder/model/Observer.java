package edu.curtin.reminder.model;

import java.util.List;

public interface Observer
{
    public void update(List<Reminder> reminders);
}
