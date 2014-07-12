package com.tenjava.entries.Axe2760.t3;

//just trying a crazy idea out ._.

import net.minecraft.server.v1_7_R3.Entity;
import net.minecraft.server.v1_7_R3.EntitySlime;
import net.minecraft.server.v1_7_R3.NBTTagCompound;
import net.minecraft.server.v1_7_R3.World;
import org.bukkit.Location;

//nuuuuuuh too much work D:
public class SlimeMan extends Entity {

    private Leg leftLeg;
    private Leg rightLeg;

    public SlimeMan(World world){


    }

    @Override
    protected void c() {
    }

    @Override
    protected void a(NBTTagCompound nbtTagCompound) {
    }

    @Override
    protected void b(NBTTagCompound nbtTagCompound) {
    }

    private class Leg{
        private boolean isLeftLeg; //left leg is opposite of the right leg - and it will move negatively with teh position
        private EntitySlime slimeBottom;
        private EntitySlime slimeTop;

        public Leg(World world, boolean isLeftLeg, Location legOrigin){
            this.isLeftLeg = isLeftLeg;
            slimeBottom = new EntitySlime(world);
            slimeTop = new EntitySlime(world);

            slimeBottom.setPosition(legOrigin.getX(), legOrigin.getY() - 1, legOrigin.getZ());
            slimeTop.setPosition(legOrigin.getX(), legOrigin.getY(), legOrigin.getZ());
            world.addEntity(slimeBottom);
            world.addEntity(slimeTop);
        }

        public void moveWithBody(int position, float yaw, float pitch){ //position is the y value of the function y = cos(x).
            if (isLeftLeg){
                position = position*-1;
            }

            double dX = 0;
            double dZ = 0;

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
            this.slimeTop.setLocation(slimeTop.locX + dX * 0.75, slimeTop.locY, slimeTop.locZ + dZ * 0.75, yaw, pitch);
            this.slimeBottom.setLocation(slimeBottom.locX + dX, slimeBottom.locY, slimeTop.locZ + dZ, yaw, pitch);

        }
    }
}
