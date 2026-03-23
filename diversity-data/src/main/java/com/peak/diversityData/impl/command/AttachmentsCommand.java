package com.peak.diversityData.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.peak.diversityCore.impl.DiversityCore;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import com.peak.diversityData.impl.TestAttachment;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class AttachmentsCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment ignoredDedicated) {
        dispatcher.register(literal("attachments")
                .then(literal("get")
                        .then(argument("target", EntityArgumentType.entity())
                                .executes(context -> {
                                    Entity entity = EntityArgumentType.getEntity(context, "target");

                                    AttachmentHolder holder = entity.diversity$getAttachmentHolder();
                                    if (holder != null) {
                                        List<Identifier> identifiers = holder.getAttachmentIds();
                                        if (!identifiers.isEmpty()) {
                                            StringBuilder builder = new StringBuilder();

                                            identifiers.forEach(id -> {
                                                builder.append(id);
                                                if (id != identifiers.getLast()) builder.append(", ");
                                            });

                                            context.getSource().sendFeedback(() -> Text.translatable("commands.attachments.get", entity.getNameForScoreboard(), builder.toString()), false);
                                        } else {
                                            context.getSource().sendFeedback(() -> Text.translatable("commands.attachments.get.none", entity.getNameForScoreboard()), false);
                                        }
                                    } else {
                                        context.getSource().sendError(Text.translatable("commands.attachments.get.failed", entity.getNameForScoreboard()));
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                ).then(literal("test")
                        .then(argument("amount", IntegerArgumentType.integer(0))
                                .executes(context -> {
                                    ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

                                    AttachmentHolder holder = player.diversity$getAttachmentHolder();

                                    holder.getAttachment(DiversityCore.id("test"), TestAttachment.class).ifPresent(test -> {
                                        test.setTestInt(IntegerArgumentType.getInteger(context, "amount"));
                                    });

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        );
    }
}
