// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.npc.nms;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class v1_8Path extends PathfinderGoal
{
    private EntityCreature b;
    protected double a;
    private double c;
    private double d;
    private double e;
    
    public v1_8Path(final EntityCreature entitycreature, final double d0, final double x, final double y, final double z) {
        this.b = entitycreature;
        this.a = d0;
        this.d = y;
        this.c = x;
        this.e = z;
    }
    
    public boolean a() {
        final Vec3D vec3d = RandomPositionGenerator.a(this.b, 5, 4);
        return vec3d != null;
    }
    
    public void c() {
        final Vec3D vec3d = RandomPositionGenerator.a(this.b, 5, 4);
        if (vec3d == null) {
            return;
        }
        this.b.getNavigation().a(this.c, this.d, this.e, 2.0);
    }
    
    public boolean b() {
        if (this.b.ticksLived - this.b.hurtTimestamp > 100) {
            this.b.b((EntityLiving)null);
            return false;
        }
        return !this.b.getNavigation().m();
    }
}
