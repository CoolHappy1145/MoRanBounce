package org.spongepowered.asm.mixin.injection.selectors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.refmap.IReferenceMapper;
import org.spongepowered.asm.util.asm.ElementNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/TargetSelector.class */
public final class TargetSelector {

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/TargetSelector$Result.class */
    public static class Result {
        public final Object exactMatch;
        public final List candidates;

        Result(Object obj, List list) {
            this.exactMatch = obj;
            this.candidates = list;
        }

        public Object getSingleResult(boolean z) {
            int size = this.candidates.size();
            if (this.exactMatch != null) {
                return this.exactMatch;
            }
            if (size == 1 || !z) {
                return this.candidates.get(0);
            }
            throw new IllegalStateException((size == 0 ? "No" : "Multiple") + " candidates were found");
        }
    }

    private TargetSelector() {
    }

    public static ITargetSelector parseAndValidate(String str) {
        return parse(str, null, null).validate();
    }

    public static ITargetSelector parseAndValidate(String str, IMixinContext iMixinContext) {
        return parse(str, iMixinContext.getReferenceMapper(), iMixinContext.getClassRef()).validate();
    }

    public static ITargetSelector parse(String str) {
        return parse(str, null, null);
    }

    public static ITargetSelector parse(String str, IMixinContext iMixinContext) {
        return parse(str, iMixinContext.getReferenceMapper(), iMixinContext.getClassRef());
    }

    public static String parseName(String str, IMixinContext iMixinContext) {
        ITargetSelector iTargetSelector = parse(str, iMixinContext);
        if (!(iTargetSelector instanceof ITargetSelectorByName)) {
            return str;
        }
        String name = ((ITargetSelectorByName) iTargetSelector).getName();
        return name != null ? name : str;
    }

    private static ITargetSelector parse(String str, IReferenceMapper iReferenceMapper, String str2) {
        return MemberInfo.parse(str, iReferenceMapper, str2);
    }

    public static Result run(ITargetSelector iTargetSelector, List list) {
        ArrayList arrayList = new ArrayList();
        return new Result(runSelector(iTargetSelector, list, arrayList), arrayList);
    }

    public static Result run(Iterable iterable, List list) {
        Object obj = null;
        ArrayList arrayList = new ArrayList();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            Object objRunSelector = runSelector((ITargetSelector) it.next(), list, arrayList);
            if (obj == null) {
                obj = objRunSelector;
            }
        }
        return new Result(obj, arrayList);
    }

    private static Object runSelector(ITargetSelector iTargetSelector, List list, List list2) {
        Object obj = null;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ElementNode elementNode = (ElementNode) it.next();
            MatchResult matchResultMatch = iTargetSelector.match(elementNode);
            if (matchResultMatch.isMatch()) {
                Object obj2 = elementNode.get();
                if (!list2.contains(obj2)) {
                    list2.add(obj2);
                }
                if (obj == null && matchResultMatch.isExactMatch()) {
                    obj = obj2;
                }
            }
        }
        return obj;
    }
}
