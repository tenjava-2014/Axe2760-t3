package com.tenjava.entries.Axe2760.t3;

//just trying a crazy idea out ._.

import net.minecraft.server.v1_7_R3.*;
import org.bukkit.Location;

public class SlimeMan extends EntityCreature{

    private Head head;
    private Leg leftLeg;
    private Leg rightLeg;
    private Torso torso;
    private Arm rightArm;
    private Arm leftArm;

    public SlimeMan(World world){
        super(world);

        this.yaw = 0;
        rightArm = new Arm(world, this, false, new Location(world.getWorld(),locX + 0.85, locY+2, locZ));
        leftArm = new Arm(world,this,true, new Location(world.getWorld(), locX - 0.85, locY+2,locZ));
        head = new Head(world, this, new Location(world.getWorld(), locX, locY + 4, locZ));
        torso = new Torso(world, this, new Location(world.getWorld(), locX, locY+2, locZ));
        leftLeg = new Leg(world, this, true, new Location(world.getWorld(),locX-0.5,locY,locZ));
        rightLeg = new Leg(world,this,false, new Location(world.getWorld(),locX + 0.5, locY , locZ));

        this.getNavigation().b(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));

    }


    private abstract class Part{
        protected Entity owner;
        public abstract RelativeCoordinates moveWithBody(int position, float yaw, float pitch);
    }

    private abstract class DoublePart extends Part{
        protected boolean isLeft; //left leg is opposite of the right leg - and it will move negatively with teh position

        public RelativeCoordinates moveWithBody(int position, float yaw, float pitch){ //position is the y value of the function y = cos(x).
            if (isLeft){
                position = position*-1;
            }

            double dX = 0;
            double dZ = 0;
            double dY = 0;
            double movingAmount = 0.5 * position;
            //yaw = 0 : +z
            //yaw = 180 : -z
            //yaw = 90 : -x
            //yaw = 270: +x

            if (yaw % 90 > 45){
                if (0 < yaw && yaw < 90){
                    dZ = movingAmount * 0.75;
                    dX = movingAmount * -0.75;
                }
                else if (90 < yaw && yaw < 180){
                    dX = movingAmount * -0.75;
                    dZ = movingAmount * -0.75;
                }
                else if (180 < yaw && yaw < 270){
                    dZ = movingAmount * -0.75;
                    dX = movingAmount * 0.75;
                }
                else if (270 < yaw){
                    dX = movingAmount * 0.75;
                    dZ = movingAmount * 0.75;
                }
            }
            else if (yaw % 90 < 45 && yaw % 90 != 0){
                if (0 < yaw && yaw < 90){
                    dZ = movingAmount * 0.25;
                    dX = movingAmount * -0.25;
                }
                else if (90 < yaw && yaw < 180){
                    dX = movingAmount * -0.25;
                    dZ = movingAmount * -0.25;
                }
                else if (180 < yaw && yaw < 270){
                    dZ = movingAmount * -0.25;
                    dX = movingAmount * 0.25;
                }
                else if (270 < yaw){
                    dX = movingAmount * 0.25;
                    dZ = movingAmount * 0.25;
                }
            }
            else if (yaw % 90 == 45){
                if (0 < yaw && yaw < 90){
                    dZ = movingAmount * 0.5;
                    dX = movingAmount * -0.5;
                }
                else if (90 < yaw && yaw < 180){
                    dX = movingAmount * -0.5;
                    dZ = movingAmount * -0.5;
                }
                else if (180 < yaw && yaw < 270){
                    dZ = movingAmount * -0.5;
                    dX = movingAmount * 0.5;
                }
                else if (270 < yaw){
                    dX = movingAmount * 0.5;
                    dZ = movingAmount * 0.5;
                }
            }
            else if (yaw % 90 == 0){
                if (yaw == 0){
                    dZ = movingAmount;
                }
                else if (yaw == 90){
                    dX = movingAmount * -1;
                }
                else if (yaw == 180){
                    dZ = movingAmount * -1;
                }
                else if (yaw == 270){
                    dX = movingAmount * 1;
                }
            }

            return new RelativeCoordinates(dX, dY, dZ);
        }
    }

    public class Leg extends DoublePart{
        private EntitySlimePart slimeBottom;
        private EntitySlimePart slimeTop;

        public Leg(World world, Entity owner, boolean isLeft, Location legOrigin){
            this.owner = owner;
            this.isLeft = isLeft;
            slimeBottom = new EntitySlimePart(world,this.owner);
            slimeTop = new EntitySlimePart(world, this.owner);

            slimeBottom.setPosition(legOrigin.getX(), legOrigin.getY() - 1, legOrigin.getZ());
            slimeTop.setPosition(legOrigin.getX(), legOrigin.getY(), legOrigin.getZ());
            world.addEntity(slimeBottom);
            world.addEntity(slimeTop);
        }

        @Override
        public RelativeCoordinates moveWithBody(int position, float yaw, float pitch) {
            RelativeCoordinates these = super.moveWithBody(position, yaw, pitch);
            this.slimeTop.setLocation(slimeTop.locX + these.dX * 0.75, slimeTop.locY, slimeTop.locZ + these.dZ * 0.75, yaw, pitch);
            this.slimeBottom.setLocation(slimeBottom.locX + these.dX, slimeBottom.locY, slimeBottom.locZ + these.dZ, yaw, pitch);
            return these;
        }
    }
    public class Torso extends Part{

        private EntitySlimePart torsoPart;

        public Torso(World world, Entity owner, Location origin){
            torsoPart = new EntitySlimePart(world,this.owner,2);
            torsoPart.setPosition(origin.getX(), origin.getY(), origin.getZ());
            world.addEntity(torsoPart);
        }

        @Override
        public RelativeCoordinates moveWithBody(int position, float yaw, float pitch) {
            //the torso will bob a tiny bit
            double dY = Math.max(Math.min(position, 0), -0.5); //changing the wave shape
            torsoPart.setLocation(torsoPart.locX, torsoPart.locY + dY, torsoPart.locZ, yaw, pitch);
            return new RelativeCoordinates(0,dY,0);
        }

    }

    private class Head extends Part{ //easiest way to do this
        private EntitySlimePart headPart;

        public Head(World world, Entity owner, Location origin){
            headPart = new EntitySlimePart(world, this.owner, 1);
            headPart.setPosition(origin.getX(), origin.getY(), origin.getZ());
            world.addEntity(headPart);
        }

        @Override
        public RelativeCoordinates moveWithBody(int position, float yaw, float pitch){
            //another bob...just a litle more, though.

            double dY = Math.max(Math.min(position, 0.5), -0.5);
            headPart.setLocation(headPart.locX, headPart.locY + dY, headPart.locZ, yaw, pitch);
            return new RelativeCoordinates(0,dY, 0);
        }
    }

    private class Arm extends DoublePart{
        private EntitySlimePart armTop;
        private EntitySlimePart armBottom;

        public Arm(World world, Entity owner, boolean isLeft, Location origin){
            this.isLeft = isLeft;
            armTop = new EntitySlimePart(world, this.owner, 1);
            armBottom = new EntitySlimePart(world,this.owner, 1);
            armTop.setPosition(origin.getX(),origin.getY(),origin.getZ());
            armBottom.setPosition(origin.getX(), origin.getY() - 0.8, origin.getZ());

            world.addEntity(armTop);
            world.addEntity(armBottom);
        }

        @Override
        public RelativeCoordinates moveWithBody(int position, float yaw, float pitch) {
            RelativeCoordinates these = super.moveWithBody(position,yaw,pitch);
            armTop.setLocation(armTop.locX + these.dX * .75, armTop.locY, armTop.locZ + these.dZ * .75, yaw, pitch);
            armBottom.setLocation(armBottom.locX + these.dX, armBottom.locY, armBottom.locZ + these.dZ, yaw, pitch);
            return these;
        }
    }


    public class EntitySlimePart extends EntitySlime{
        private Entity owner;

        public EntitySlimePart(World world){
            super(world);
        }

        public EntitySlimePart(World world, Entity owner){
            super(world);
            this.owner = owner;
        }

        public EntitySlimePart(World world, Entity owner, int size){
            super(world);
            this.owner = owner;
            this.setSize(size);
        }

        public boolean damageEntity(DamageSource source, float f){
            return owner.damageEntity(source, f);
        }
    }

    private class RelativeCoordinates{
        public double dX;
        public double dY;
        public double dZ;

        private RelativeCoordinates(double dX, double dY, double dZ) {
            this.dX = dX;
            this.dY = dY;
            this.dZ = dZ;
        }
    }

}
