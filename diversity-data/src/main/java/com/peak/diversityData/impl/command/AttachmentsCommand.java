package com.peak.diversityData.impl.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.peak.diversityData.features.attachment.AttachmentHolder;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
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

                                            context.getSource().sendFeedback(() -> Text.translatable("commands.attachments.get", entity.getNameForScoreboard(), builder), false);
                                        } else {
                                            context.getSource().sendFeedback(() -> Text.translatable("commands.attachments.get.none", entity.getNameForScoreboard()), false);
                                        }
                                    } else {
                                        context.getSource().sendError(Text.translatable("commands.attachments.get.failed", entity.getNameForScoreboard()));
                                    }

                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        );
    }
}
