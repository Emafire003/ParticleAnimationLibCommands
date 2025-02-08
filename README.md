# ParticleAnimationLibCommands
![pal_commands_banner_logo](https://github.com/user-attachments/assets/b5eccb4f-a669-4607-be4e-f91c578c9b38)
A companion mod for ParticleAnimationLib adding commands to summon the effects and manage them!
It is the user (non developer) friendly-way of interacting with the particle effects added by PAL!

Do you want to create cool particle effects such as cones, cubes, spheres, and vortices but don't know how? Well, then this mod is for you! 
It is also fully serverside, meaning you won't need it on the client. It is inspirerd by the [EffectLib](https://github.com/elBukkit/EffectLib) plugin.

Currently available effects:
- [3D] Animated Sphere, Sphere, Cuboid, Vortex, Cone, Donut
- [2D] Arc, Line, Animated Circle, Images, Text

<center>
  
<p align="center">
  <img src="https://github.com/Emafire003/ParticleAnimationLib/assets/29462910/f3614984-c6c8-4fd1-ac5b-0ed9adef732a" alt="Demo of some of the effects from version 0.0.1" />
</p>

</center>

*Demo of some of the effects from version 0.0.1*

## How do I use the commands?
You can use either `pal` or `particleanimationlib` and then use tab-completition to create your effects. They have a lot of parameters so you should probably consult the wiki first: TODO wiki link!

You can quickly spawn in an effect demo using `/pal <effectname> demo <particle> <position> <duration>`.

## Why a separate project?
Mostly beacuse this is easier to mantain. My main goal is develop the library for development use, so the commands may lack behind sometimes. On top of that, commands usually change more frequently between version, so the core of the mod can work for multiple version without making separate builds for every minecraft version. Anyway, if anyone is interest in helping mantain this side of the project, let me know!

## Credit
This mod (well, the library mod PAL) is based on the bukkit plugin [EffectLib](https://github.com/elBukkit/EffectLib) by elBukkit team, check it out too! The effects are taken from there and tweaked to be compatible with modding, occasionally adding extra functionality.

## License
This library is available under MIT license
