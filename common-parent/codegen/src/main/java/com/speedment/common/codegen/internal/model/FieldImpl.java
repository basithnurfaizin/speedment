/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.AnnotationUsage;
import com.speedment.common.codegen.model.Field;
import com.speedment.common.codegen.model.Import;
import com.speedment.common.codegen.model.Javadoc;
import com.speedment.common.codegen.model.Value;
import com.speedment.common.codegen.model.modifier.Modifier;
import com.speedment.common.codegen.model.trait.HasFields;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link Field} interface.
 * This class should not be instantiated directly. Instead you should call the
 * {@link Field#of(String, Type)} method to get an instance. In that way, you 
 * can later change the implementing class without modifying the using code.
 * 
 * @author Emil Forslund
 * @see    Field
 */
public final class FieldImpl implements Field {

	private HasFields<?> parent;
	private String name;
	private Type type;
	private Value<?> value;
	private Javadoc javadoc;
	private final List<Import> imports;
	private final List<AnnotationUsage> annotations;
	private final Set<Modifier> modifiers;
	
    /**
     * Initializes this field using a name and a type.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using 
     * the {@link Field#of(String, Type)} method!
     * 
     * @param name  the name
     * @param type  the type
     */
	public FieldImpl(String name, Type type) {
		this.name			= requireNonNull(name);
		this.type			= requireNonNull(type);
		this.value			= null;
		this.javadoc		= null;
		this.imports    	= new ArrayList<>();
		this.annotations	= new ArrayList<>();
		this.modifiers		= EnumSet.noneOf(Modifier.class);
	}
	
    /**
     * Copy constructor.
     * 
     * @param prototype  the prototype
     */
	protected FieldImpl(Field prototype) {
	    name		= requireNonNull(prototype).getName();
		type		= prototype.getType();
		imports		= Copier.copy(prototype.getImports());
		value		= prototype.getValue().map(Copier::copy).orElse(null);
		javadoc		= prototype.getJavadoc().map(Copier::copy).orElse(null);
		annotations	= Copier.copy(prototype.getAnnotations());
		modifiers	= Copier.copy(prototype.getModifiers(), Modifier::copy, EnumSet.noneOf(Modifier.class));
	}

    @Override
    public Field setParent(HasFields<?> parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public Optional<HasFields<?>> getParent() {
        return Optional.ofNullable(parent);
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
	public String getName() {
		return name;
	}

	@Override
	public Field setName(String name) {
		this.name = requireNonNull(name);
		return this;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public Field set(Type type) {
		this.type = requireNonNull(type);
		return this;
	}

	@Override
	public Set<Modifier> getModifiers() {
		return modifiers;
	}

	@Override
	public Field set(Javadoc doc) {
		javadoc = doc.setParent(this);
		return this;
	}

	@Override
	public Optional<Javadoc> getJavadoc() {
		return Optional.ofNullable(javadoc);
	}

	@Override
	public Field set(Value<?> val) {
		this.value = val;
		return this;
	}

	@Override
	public Optional<Value<?>> getValue() {
		return Optional.ofNullable(value);
	}

	@Override
	public List<AnnotationUsage> getAnnotations() {
		return annotations;
	}

    @Override
	public FieldImpl copy() {
		return new FieldImpl(this);
	}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.type);
        hash = 29 * hash + Objects.hashCode(this.value);
        hash = 29 * hash + Objects.hashCode(this.javadoc);
        hash = 29 * hash + Objects.hashCode(this.imports);
        hash = 29 * hash + Objects.hashCode(this.annotations);
        hash = 29 * hash + Objects.hashCode(this.modifiers);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FieldImpl other = (FieldImpl) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.javadoc, other.javadoc)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
        if (!Objects.equals(this.annotations, other.annotations)) {
            return false;
        }
        return Objects.equals(this.modifiers, other.modifiers);
    }


}