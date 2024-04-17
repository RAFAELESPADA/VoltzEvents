// 
// Decompiled by Procyon v0.5.36
// 

package br.com.stenox.training.utils.hologram;

import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;

public class NullBoundingBox extends AxisAlignedBB
{
    public NullBoundingBox() {
        super(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public double a() {
        return 0.0;
    }
    
    public AxisAlignedBB a(final AxisAlignedBB arg0) {
        return this;
    }
    
    public double a(final AxisAlignedBB arg0, final double arg1) {
        return 0.0;
    }
    
    public AxisAlignedBB a(final double arg0, final double arg1, final double arg2) {
        return this;
    }
    
    public boolean a(final Vec3D arg0) {
        return false;
    }
    
    public MovingObjectPosition a(final Vec3D arg0, final Vec3D arg1) {
        return super.a(arg0, arg1);
    }
    
    public boolean b(final AxisAlignedBB arg0) {
        return false;
    }
    
    public double b(final AxisAlignedBB arg0, final double arg1) {
        return 0.0;
    }
    
    public double c(final AxisAlignedBB arg0, final double arg1) {
        return 0.0;
    }
    
    public AxisAlignedBB c(final double arg0, final double arg1, final double arg2) {
        return this;
    }
    
    public AxisAlignedBB grow(final double arg0, final double arg1, final double arg2) {
        return this;
    }
    
    public AxisAlignedBB shrink(final double arg0, final double arg1, final double arg2) {
        return this;
    }
}
