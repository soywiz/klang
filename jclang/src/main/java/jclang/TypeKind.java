

package jclang;

import org.jetbrains.annotations.NotNull;
import jclang.structs.CXString;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum TypeKind {
    INVALID,
    UNEXPOSED,
    VOID,
    BOOL,
    CHAR_U,
    UCHAR,
    CHAR16,
    CHAR32,
    USHORT,
    UINT,
    ULONG,
    ULONG_LONG,
    UINT128,
    CHAR_S,
    SCHAR,
    WCHAR,
    SHORT,
    INT,
    LONG,
    LONG_LONG,
    INT128,
    FLOAT,
    DOUBLE,
    LONG_DOUBLE,
    NULL_PTR,
    OVERLOAD,
    DEPENDENT,
    OBJC_ID,
    OBJC_CLASS,
    OBJC_SEL,

    COMPLEX,
    POINTER,
    BLOCK_POINTER,
    LVALUE_REFERENCE,
    RVALUE_REFERENCE,
    RECORD,
    ENUM,
    TYPEDEF,
    OBJC_INTERFACE,
    OBJC_OBJECT_POINTER,
    FUNCTION_NO_PROTO,
    FUNCTION_PROTO,
    CONSTANT_ARRAY,
    VECTOR,
    Incompletearray;

    private static final Map<Integer, TypeKind> FROM_NATIVE = new HashMap<Integer, TypeKind>();
    private static final Map<TypeKind, Integer> TO_NATIVE = new EnumMap<TypeKind, Integer>(TypeKind.class);

    static {
        // The code below depends on the actual CXCursorKind enum values
        int nativeValue = 0;
        for (TypeKind enumValue : values()) {
            if (enumValue == COMPLEX) {
                nativeValue = 100;
            }
            FROM_NATIVE.put(nativeValue, enumValue);
            TO_NATIVE.put(enumValue, nativeValue);
            nativeValue++;
        }
    }

    @NotNull
    /* package */ static TypeKind fromNative(int kind) {
        TypeKind result = FROM_NATIVE.get(kind);
        if (result == null) {
            throw new IllegalStateException("Unknown CXTypeKind value: " + kind + ". Probably an incompatible libclang version");
        }
        return result;
    }

    /* package */ int toNative() {
        Integer result = TO_NATIVE.get(this);
        if (result == null) {
            throw new IllegalStateException("No corresponding CXTypeKind value: " + this + ". Probably an incompatible libclang version");
        }
        return result;
    }

    @NotNull
    public String getSpelling() {
        CXString.ByValue spelling = LibClang.I.getTypeKindSpelling(toNative());
        NativePool.I.record(spelling);
        return LibClang.I.getCString(spelling);
    }
}
