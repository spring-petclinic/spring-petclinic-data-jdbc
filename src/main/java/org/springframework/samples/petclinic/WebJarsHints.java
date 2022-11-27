/*
 * Copyright 2012-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.TypeReference;

import java.util.List;

public class WebJarsHints implements RuntimeHintsRegistrar {
	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		hints.reflection().registerType(java.lang.Module.class, MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection().registerType(java.lang.ModuleLayer.class, MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection().registerType(java.lang.module.Configuration.class, MemberCategory.INVOKE_DECLARED_METHODS);
		hints.reflection().registerType(java.lang.module.ResolvedModule.class, MemberCategory.INVOKE_DECLARED_METHODS);

		List.of("nonapi.io.github.classgraph.classloaderhandler.AntClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.ClassGraphClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.ClassLoaderHandlerRegistry",
				"nonapi.io.github.classgraph.classloaderhandler.CxfContainerClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.EquinoxClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.EquinoxContextFinderClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.FallbackClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.FelixClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.JBossClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.JPMSClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.OSGiDefaultClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.ParentLastDelegationOrderTestClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.PlexusClassWorldsClassRealmClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.QuarkusClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.SpringBootRestartClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.TomcatWebappClassLoaderBaseHandler",
				"nonapi.io.github.classgraph.classloaderhandler.UnoOneJarClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.URLClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.WeblogicClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.WebsphereLibertyClassLoaderHandler",
				"nonapi.io.github.classgraph.classloaderhandler.WebsphereTraditionalClassLoaderHandler").forEach(
						clazz -> hints.reflection().registerType(TypeReference.of(clazz), MemberCategory.INVOKE_DECLARED_METHODS));
	}
}
