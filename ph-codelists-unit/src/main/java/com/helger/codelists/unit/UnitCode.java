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
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.text.locale.LocaleHelper;
import com.helger.text.resourcebundle.ResourceBundleHelper;

/**
 * Default implementation of {@link IUnitCode}.
 *
 * @author Philip Helger
 */
public final class UnitCode implements IUnitCode
{
  private final String m_sID;
  private final EUnitCodeSource m_eSource;
  private final String m_sName;

  /**
   * Constructor.
   *
   * @param sID
   *        The code. May neither be <code>null</code> nor empty.
   * @param eSource
   *        The recommendation the code is defined in. May not be <code>null</code>.
   * @param sName
   *        The English name. May neither be <code>null</code> nor empty.
   */
  public UnitCode (@NonNull @Nonempty final String sID,
                   @NonNull final EUnitCodeSource eSource,
                   @NonNull @Nonempty final String sName)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.notNull (eSource, "Source");
    ValueEnforcer.notEmpty (sName, "Name");
    m_sID = sID;
    m_eSource = eSource;
    m_sName = sName;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  public EUnitCodeSource getSource ()
  {
    return m_eSource;
  }

  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sName;
  }

  @NonNull
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    ValueEnforcer.notNull (aContentLocale, "ContentLocale");

    for (final Locale aLocale : LocaleHelper.getCalculatedLocaleListForResolving (aContentLocale))
    {
      // The English names are the data of the code list
      if (aLocale.getLanguage ().isEmpty () || "en".equals (aLocale.getLanguage ()))
        break;

      // The locale is part of the bundle name, to avoid the fall back to the default locale
      final String sText = ResourceBundleHelper.getUtf8String (UnitCodes.BUNDLE_BASE_NAME + "_" + aLocale,
                                                               aLocale,
                                                               m_sID);
      if (sText != null)
        return sText;
    }
    return m_sName;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (this == o)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final UnitCode rhs = (UnitCode) o;
    return EqualsHelper.equals (m_sID, rhs.m_sID) &&
           EqualsHelper.equals (m_eSource, rhs.m_eSource) &&
           EqualsHelper.equals (m_sName, rhs.m_sName);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID).append (m_eSource).append (m_sName).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("ID", m_sID)
                                       .append ("Source", m_eSource)
                                       .append ("Name", m_sName)
                                       .getToString ();
  }
}
