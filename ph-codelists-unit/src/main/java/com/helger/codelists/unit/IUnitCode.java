/*
 * Copyright (C) 2026 Philip Helger
 * http://www.helger.com
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.codelists.unit;

import java.util.Locale;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.name.IHasDisplayName;
import com.helger.text.display.IHasDisplayText;

/**
 * A single unit code of UN/ECE Recommendation N&deg;20 or N&deg;21.
 *
 * @author Philip Helger
 */
public interface IUnitCode extends IHasID <String>, IHasDisplayName, IHasDisplayText
{
  /**
   * @return The code, like <code>C62</code>, <code>KGM</code> or <code>XPP</code>. Neither
   *         <code>null</code> nor empty.
   */
  @NonNull
  @Nonempty
  String getID ();

  /**
   * @return The recommendation the code was defined in. Never <code>null</code>.
   */
  @NonNull
  EUnitCodeSource getSource ();

  /**
   * @return The English name of the unit, as published by UN/ECE. Neither <code>null</code> nor
   *         empty.
   */
  @NonNull
  @Nonempty
  String getDisplayName ();

  /**
   * Get the name of the unit in the language of the passed locale. The texts come from the
   * resource bundles <code>ph-codelists-unit_&lt;locale&gt;.properties</code> (UTF-8, the key is the
   * code); the locale is tried from the most specific to the most general one. If no bundle has the
   * code, the English name is returned.
   *
   * @param aContentLocale
   *        The locale to use. May not be <code>null</code>.
   * @return The name in the requested language or the English name. Never <code>null</code>.
   */
  @NonNull
  String getDisplayText (@NonNull Locale aContentLocale);
}
