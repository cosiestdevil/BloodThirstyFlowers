package uk.cosiestdevil.minecraft.bloodthirstyflowers;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import vazkii.botania.common.lib.ModTags;

public class BlockTagsGenerator extends BlockTagsProvider {
    public BlockTagsGenerator(DataGenerator generatorIn, ExistingFileHelper exFileHelper) {
        super(generatorIn, BloodThirstyFlowerMod.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ModTags.Blocks.FLOATING_FLOWERS).add(BloodThirstyFlowerMod.haemoflorFloating.get());
    }
}