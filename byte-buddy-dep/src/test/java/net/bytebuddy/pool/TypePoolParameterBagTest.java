package net.bytebuddy.pool;

import net.bytebuddy.instrumentation.ModifierReviewable;
import org.junit.Test;
import org.objectweb.asm.Type;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class TypePoolParameterBagTest {

    private static final String FOO = "foo", BAR = "bar", QUX = "qux";

    @Test
    public void testFullResolutionStaticMethod() throws Exception {
        Type[] type = new Type[3];
        type[0] = Type.getType(Object.class);
        type[1] = Type.getType(long.class);
        type[2] = Type.getType(String.class);
        TypePool.Default.ParameterBag parameterBag = new TypePool.Default.ParameterBag(type);
        parameterBag.register(0, FOO);
        parameterBag.register(1, BAR);
        parameterBag.register(3, QUX);
        List<TypePool.LazyTypeDescription.MethodToken.ParameterToken> tokens = parameterBag.resolve(true);
        assertThat(tokens.size(), is(3));
        assertThat(tokens.get(0).getName(), is(FOO));
        assertThat(tokens.get(1).getName(), is(BAR));
        assertThat(tokens.get(2).getName(), is(QUX));
        assertThat(tokens.get(0).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(1).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(2).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
    }

    @Test
    public void testFullResolutionNonStaticMethod() throws Exception {
        Type[] type = new Type[3];
        type[0] = Type.getType(Object.class);
        type[1] = Type.getType(long.class);
        type[2] = Type.getType(String.class);
        TypePool.Default.ParameterBag parameterBag = new TypePool.Default.ParameterBag(type);
        parameterBag.register(1, FOO);
        parameterBag.register(2, BAR);
        parameterBag.register(4, QUX);
        List<TypePool.LazyTypeDescription.MethodToken.ParameterToken> tokens = parameterBag.resolve(false);
        assertThat(tokens.size(), is(3));
        assertThat(tokens.get(0).getName(), is(FOO));
        assertThat(tokens.get(1).getName(), is(BAR));
        assertThat(tokens.get(2).getName(), is(QUX));
        assertThat(tokens.get(0).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(1).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(2).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
    }

    @Test
    public void testPartlyResolutionStaticMethod() throws Exception {
        Type[] type = new Type[3];
        type[0] = Type.getType(Object.class);
        type[1] = Type.getType(long.class);
        type[2] = Type.getType(String.class);
        TypePool.Default.ParameterBag parameterBag = new TypePool.Default.ParameterBag(type);
        parameterBag.register(0, FOO);
        parameterBag.register(3, QUX);
        List<TypePool.LazyTypeDescription.MethodToken.ParameterToken> tokens = parameterBag.resolve(true);
        assertThat(tokens.size(), is(3));
        assertThat(tokens.get(0).getName(), is(FOO));
        assertThat(tokens.get(1).getName(), nullValue(String.class));
        assertThat(tokens.get(2).getName(), is(QUX));
        assertThat(tokens.get(0).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(1).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(2).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
    }

    @Test
    public void testEmptyResolutionStaticMethod() throws Exception {
        Type[] type = new Type[3];
        type[0] = Type.getType(Object.class);
        type[1] = Type.getType(long.class);
        type[2] = Type.getType(String.class);
        TypePool.Default.ParameterBag parameterBag = new TypePool.Default.ParameterBag(type);
        List<TypePool.LazyTypeDescription.MethodToken.ParameterToken> tokens = parameterBag.resolve(true);
        assertThat(tokens.size(), is(3));
        assertThat(tokens.get(0).getName(), nullValue(String.class));
        assertThat(tokens.get(1).getName(), nullValue(String.class));
        assertThat(tokens.get(2).getName(), nullValue(String.class));
        assertThat(tokens.get(0).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(1).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
        assertThat(tokens.get(2).getModifiers(), is(ModifierReviewable.EMPTY_MASK));
    }
}