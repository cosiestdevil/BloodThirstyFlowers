package uk.cosiestdevil.minecraft.bloodthirstyflowers;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.subtile.generating.SubTileFluidGenerator;
import vazkii.botania.common.handler.ModSounds;

public class SubTileBloodThirsty extends SubTileFluidGenerator {
    public SubTileBloodThirsty(BlockPos pos, BlockState state) {
        super(BloodThirstyFlowerMod.HAEMOFLOR.get(), pos, state, BloodThirstyFlowerMod.LIFE, 900, 30, 6000);
    }

    @Override
    public int getColor() {
        return 0xFF3C00;
    }

    @Override
    public void doBurnParticles() {
        WispParticleData data = WispParticleData.wisp((float) Math.random() / 6, 1F, 0.05F, 0.05F, 1);
        emitParticle(data, 0.5 + Math.random() * 0.2 - 0.1, 0.9 + Math.random() * 0.2 - 0.1, 0.5 + Math.random() * 0.2 - 0.1, 0, (float) Math.random() / 60, 0);
    }

    @Override
    public void playSound() {
        getLevel().playSound(null, getEffectivePos(), ModSounds.thermalily, SoundSource.BLOCKS, 1F, 1F);
    }

    @Override
    public int getMaxMana() {
        return 500;
    }
}
