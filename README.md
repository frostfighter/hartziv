HartzIV
=======

A bukkit economy plugin


Plugin here:
http://dev.bukkit.org/bukkit-plugins/hartziv/

This plugin lets the players on your server get money by right clicking on a sign.
How it works

Create a [MoneyGet]-Sign. Players can get money by right clicking on the sign.

Players who clicked on the sign don't get money until they have waited the waiting time which is set in the config.

That looks like this:

Chat and sign
The [MoneyGet]-Sign

    [MoneyGet]
    Amount
    empty
    empty 

An example sign
Permissions

    hartziv.sign.create - Allows players to create a MoneyGet sign; default: op 

Commands

No commands
External Plugins

This plugin needs Vault and an economy plugin like iConomy.
Config

    wait - defines in milliseconds how long players have to wait between one click and the next
    players - subentries define when the players last clicked on a sign. Do not modify. 

Planned features

    Permission: hartziv.sign.use - Allows players to click on a sign default: true
    Set the waiting time in the sign
    Plugin only gives players money if their budget is low
    Maybe Commands 

Source code here:

https://github.com/frostfighter/hartziv/




By the way:
The name of my project comes from the German system "HartzIV". Google that if you want to get out about this.
