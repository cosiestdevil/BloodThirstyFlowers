package uk.cosiestdevil.minecraft.bloodthirstyflowers;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import vazkii.botania.api.BotaniaForgeClientCapabilities;
import vazkii.botania.api.subtile.TileEntityGeneratingFlower;
import vazkii.botania.client.render.tile.RenderTileSpecialFlower;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.block.ItemBlockSpecialFlower;
import vazkii.botania.forge.CapabilityUtil;

import static vazkii.botania.common.lib.ResourceLocationHelper.prefix;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BloodThirstyFlowerMod.MODID)
public class BloodThirstyFlowerMod
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "bloodthirstyflowers";
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,MODID);
    public static final RegistryObject<Block> haemoflor = BLOCKS.register("haemoflor", () -> new vazkii.botania.common.block.BlockSpecialFlower(MobEffects.FIRE_RESISTANCE, 120, BlockBehaviour.Properties.copy(Blocks.POPPY), BloodThirstyFlowerMod.HAEMOFLOR::get));

    public static final RegistryObject<Block> haemoflorFloating = BLOCKS.register("floating_haemoflor", ()->new vazkii.botania.common.block.BlockFloatingSpecialFlower(vazkii.botania.common.block.ModBlocks.FLOATING_PROPS, BloodThirstyFlowerMod.HAEMOFLOR::get));
    public static final RegistryObject<BlockEntityType<SubTileBloodThirsty>> HAEMOFLOR = BLOCK_ENTITY_TYPES.register("haemoflor",()->BlockEntityType.Builder.of(SubTileBloodThirsty::new, haemoflor.get(), haemoflorFloating.get()).build(null));
    public static final RegistryObject<Item> haemoflorItem = ITEMS.register("haemoflor",()->new ItemBlockSpecialFlower(haemoflor.get(), ModItems.defaultBuilder()));
    public static final RegistryObject<Item> haemoflorFloatingItem = ITEMS.register("floating_haemoflor",()->new ItemBlockSpecialFlower(haemoflorFloating.get(), ModItems.defaultBuilder()));
    public static final TagKey<Fluid> LIFE = ForgeRegistries.FLUIDS.tags().createTagKey(new ResourceLocation("forge", "life"));
    //public static final BlockEntityType<SubTileThermalily> THERMALILY = IXplatAbstractions.INSTANCE.createBlockEntityType(SubTileThermalily::new, thermalily, thermalilyFloating);
    public BloodThirstyFlowerMod()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::gatherData);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerEntityRenderers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onModelRegister);
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }


    public void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        evt.registerBlockEntityRenderer(HAEMOFLOR.get(), RenderTileSpecialFlower::new);
    }

    public void onModelRegister(ModelRegistryEvent evt) {
        ItemBlockRenderTypes.setRenderLayer(haemoflor.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(haemoflorFloating.get(), RenderType.cutout());
    }
    @SubscribeEvent
    public void attachBeCapabilities(AttachCapabilitiesEvent<BlockEntity> e) {
        var be = e.getObject();
        if (be instanceof SubTileBloodThirsty) {
            e.addCapability(prefix("wand_hud"),
                    CapabilityUtil.makeProvider(BotaniaForgeClientCapabilities.WAND_HUD, new TileEntityGeneratingFlower.GeneratingWandHud<>((SubTileBloodThirsty) be)));
        }
    }

    public void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        gen.addProvider(new BlockTagsGenerator(gen,event.getExistingFileHelper()));

    }
}
