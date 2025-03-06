package com.xcompwiz.lookingglass.transformers;

import com.xcompwiz.lookingglass.LookingGlassPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ItemCameraMonitorRendererTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(transformedName.equals("net.geforcemods.securitycraft.renderers.ItemCameraMonitorRenderer")) {
            LookingGlassPlugin.LOGGER.info("Transforming class {} to fix camera monitor rendering", transformedName);

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            ClassReader classReader = new ClassReader(basicClass);

            classReader.accept(new ClassVisitor(Opcodes.ASM5, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                    MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);

                    if(name.equals("renderItem")) {
                        return new MethodVisitor(Opcodes.ASM5, methodVisitor) {
                            @Override
                            public void visitLdcInsn(Object cst) {
                                if(cst instanceof Double && ((Double) cst) == -1.0) {
                                    super.visitLdcInsn(1.0);
                                } else {
                                    super.visitLdcInsn(cst);
                                }
                            }
                        };
                    }

                    return methodVisitor;
                }
            }, ClassReader.EXPAND_FRAMES);

            return classWriter.toByteArray();
        }
        return basicClass;
    }
}
