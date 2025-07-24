package edu.curtin.stopwatch;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println();
        System.out.println("OOSE Worksheet 0: 'Stopwatch' demo app, JavaFX (GUI) version");
        System.out.println("If you see a pop-up window containing a simple stopwatch, then this app is successfully running.");
        System.out.println();

        int status = 1;
        try
        {
            StopWatch.start(args);
            status = 0;
        }
        catch(Exception e)
        {
            var msg = e.getMessage();
            System.out.printf("\033[31;1mError:\033[0;1m %s - %s\033[m\n\n",
                e.getClass().getName(), msg);
            if(e.getMessage().toLowerCase().contains("unable to open display"))
            {
                System.out.println("Your environment seems unable to run graphical applications. If you're using WSL (Windows Subsystem for Linux), you may need to follow the instructions here: https://learn.microsoft.com/en-us/windows/wsl/tutorials/gui-apps. If using SSH (Secure SHell), you need to install and run an X server on your local PC and enable X forwarding in your SSH program. Otherwise, consider _not_ using WSL or SSH, and trying an alternate setup instead.");
            }
        }
        System.exit(status);
    }
}
