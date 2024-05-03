/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package Acrimony.command.impl;

import Acrimony.Acrimony;
import Acrimony.command.Command;
import Acrimony.module.Module;
import Acrimony.util.misc.LogUtil;

public class Toggle
extends Command {
    public Toggle() {
        super("Toggle", "Turns on or off the specified module.", "t");
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length >= 2) {
            Object module = Acrimony.instance.getModuleManager().getModuleByNameNoSpace(args[1]);
            if (module != null) {
                ((Module)module).toggle();
                LogUtil.addChatMessage((((Module)module).isEnabled() ? "Enabled " : "Disabled ") + ((Module)module).getName());
            }
        } else {
            LogUtil.addChatMessage("Usage : .t/toggle modulename");
        }
    }
}

